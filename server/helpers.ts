require("dotenv").config();
const os = require("os");
import axios from "axios";

const { MAPBOX_ACCESS_TOKEN } = process.env;

export const getIpAddress = () =>
  Object.values(os.networkInterfaces())
    // @ts-ignore
    .flat()
    .filter((e) => e.family === "IPv4" && e.address != "127.0.0.1")[0].address;

//#region geocoding
export interface GeocodingResponse {
  type: string;
  query: string[];
  features: Feature[];
  attribution: string;
}

interface Feature {
  id: string;
  type: string;
  place_type: string[];
  relevance: number;
  properties: Properties;
  text: string;
  place_name: string;
  center: number[];
  geometry: Geometry;
  context: Context[];
  bbox?: number[];
}

interface Context {
  id: string;
  text: string;
  wikidata?: string;
  short_code?: string;
}

interface Geometry {
  coordinates: number[];
  type: string;
}

interface Properties {
  foursquare?: string;
  landmark?: boolean;
  address?: string;
  category?: string;
  maki?: string;
  wikidata?: string;
  short_code?: string;
}

//#endregion geocoding

//#region Direction
export interface DirectionResponse {
  routes: Route[];
  waypoints: Waypoint[];
  code: string;
  uuid: string;
}

interface Waypoint {
  distance: number;
  name: string;
  location: number[];
}

interface Route {
  weight_name: string;
  weight: number;
  duration: number;
  distance: number;
  legs: Leg[];
  geometry: string;
  voiceLocale: string;
}

interface Leg {
  steps: Step[];
  admins: Admin[];
  duration: number;
  distance: number;
  weight: number;
  summary: string;
}

interface Admin {
  iso_3166_1_alpha3: string;
  iso_3166_1: string;
}

interface Step {
  voiceInstructions: VoiceInstruction[];
  bannerInstructions: (BannerInstruction | BannerInstructions2)[];
  maneuver: Maneuver;
  intersections: Intersection[];
  weight: number;
  duration: number;
  distance: number;
  name: string;
  driving_side: string;
  mode: string;
  geometry: string;
}

interface Intersection {
  entry: boolean[];
  bearings: number[];
  duration?: number;
  mapbox_streets_v8?: Mapboxstreetsv8;
  is_urban?: boolean;
  admin_index: number;
  out?: number;
  weight?: number;
  geometry_index: number;
  location: number[];
  in?: number;
  turn_weight?: number;
}

interface Mapboxstreetsv8 {
  class: string;
}

interface Maneuver {
  type: string;
  instruction: string;
  bearing_after: number;
  bearing_before: number;
  location: number[];
  modifier?: string;
}

interface BannerInstructions2 {
  sub: Primary;
  primary: Primary;
  distanceAlongGeometry: number;
}

interface BannerInstruction {
  primary: Primary;
  distanceAlongGeometry: number;
}

interface Primary {
  components: Component[];
  type: string;
  modifier: string;
  text: string;
}

interface Component {
  type: string;
  text: string;
}

interface VoiceInstruction {
  ssmlAnnouncement: string;
  announcement: string;
  distanceAlongGeometry: number;
}
//#endregion Direction

export interface Location {
  longitude: number;
  latitude: number;
}

export const getGeoCode = async (place_name: string): Promise<Location> => {
  try {
    const resp = await axios.get<GeocodingResponse>(
      `https://api.mapbox.com/geocoding/v5/mapbox.places/${escape(
        place_name
      )}.json?access_token=${MAPBOX_ACCESS_TOKEN}`
    );
    const {
      data: { features },
    } = resp;
    const [first] = features;
    const [longitude, latitude] = first.center;
    console.log(longitude, latitude);
    return { longitude, latitude };
  } catch (error) {
    console.log(error);
  }
};

export const getDirection = async (
  from: Location,
  to: Location
): Promise<String[]> => {
  try {
    const url = `https://api.mapbox.com/directions/v5/mapbox/cycling/
        ${from.longitude},${from.latitude};${to.longitude},${to.latitude}
        ?steps=true
        &voice_instructions=true
        &banner_instructions=true
        &voice_units=imperial
        &waypoint_names=Home;Work
        &access_token=${MAPBOX_ACCESS_TOKEN}`.replace(/[\n ]/g, "");

    const resp = await axios.get(url);
    const {
      data: { routes },
    } = resp;
    const [selectedRoute] = routes;
    const { legs } = selectedRoute;
    const [firstLeg] = legs;
    const { steps } = firstLeg;
    const steps_announcement = steps
      .map((e) => e.voiceInstructions)
      // @ts-ignore
      .flat()
      .map(({ announcement }) => announcement);
    return steps_announcement;
  } catch (error) {
    console.log(error);
  }
};
