import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

// Stub for making the REST call
export function loginUser(dataJson) {
  return axios.post(semiEndpoint + "/api/auth/signin", dataJson);
}
