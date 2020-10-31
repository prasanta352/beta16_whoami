import * as express from "express";
import {
  getDirection,
  getGeoCode,
  getIpAddress,
  sendSms,
  chunkString,
} from "./helpers";
const app = express();
const port = 3000;
const RECEIVER_MOBILE_NO = "xxxxxxxxx";

app.get("/", async (req, res) => {
  try {
    const { query } = req;
    const { from, to } = query as { from?: string; to?: string };
    if (from && to) {
      //   const fromLocation = await getGeoCode(from);
      //   const toLocation = await getGeoCode(to);
      //   const steps = await getDirection(fromLocation, toLocation);
      const steps = [
        "Head southwest, then turn left onto Motilal Nehru Marg",
        "Turn left onto Motilal Nehru Marg, then enter the roundabout and take the 4th exit onto Shahjahan Road",
        "Enter the roundabout and take the 4th exit onto Shahjahan Road",
        "In 600 feet, turn right onto Humayun Road",
        "Turn right onto Humayun Road",
        "In 700 feet, turn left onto Shri Barada Ukil Marg",
        "Turn left onto Shri Barada Ukil Marg, then you will arrive at your destination",
        "You have arrived at Work, on the right",
      ];
      console.log(steps);

      steps.forEach((step, i) => {
        sendSms(RECEIVER_MOBILE_NO, `${i}. ${step}`);
      });

      return res.send(steps);
    }
  } catch (error) {}
  res.send([]);
});

app.listen(port, () => {
  console.log(`app listening at http://${getIpAddress()}:${port}`);
});

// (async () => {
//   const from: Location = await getGeoCode("Taj Mahal new Dilhi INDIA");
//   const to: Location = await getGeoCode(
//     "Raghubir Singh Junior Modern School Dilhi INDIA"
//   );
//   const steps = await getDirection(from, to);
//   console.log(steps);
// })();
