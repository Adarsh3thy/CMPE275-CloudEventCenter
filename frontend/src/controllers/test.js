import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

// Stub for making the REST call
export function testAPICall(dataJson, token) {
  axios.defaults.headers.common["authorization"] = token;
  return axios.post(semiEndpoint + "/checkout", dataJson);
}
