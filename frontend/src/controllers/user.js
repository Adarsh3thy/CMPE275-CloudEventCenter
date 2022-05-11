import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getUserDetails(userId) {
  return axios.get(semiEndpoint + "/api/auth/user/" + userId);
}

export function updateUserDetails(userId, dataJson) {
  return axios.put(semiEndpoint + "/api/auth/user/" + userId, dataJson);
}
