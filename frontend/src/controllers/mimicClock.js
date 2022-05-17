import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getMimicTime() {
  return axios.get(semiEndpoint + "/api/clock/gettime");
}

export function setMimicTime(newDate) {
  return axios.post(semiEndpoint + "/api/clock/settime/" + newDate);
}
