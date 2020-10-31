import * as express from "express";
import { getDirection, getGeoCode, getIpAddress } from "./helpers";
const app = express();
const port = 3000;

app.get("/", async (req, res) => {
  try {
    const { query } = req;
    const { from, to } = query as { from?: string; to?: string };
    if (from && to) {
      const fromLocation = await getGeoCode(from);
      const toLocation = await getGeoCode(to);
      const steps = await getDirection(fromLocation, toLocation);

      console.log(fromLocation, toLocation, steps);
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
