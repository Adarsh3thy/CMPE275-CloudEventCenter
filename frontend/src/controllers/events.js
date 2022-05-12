import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getEvents(page = 1) {
  return axios.get(semiEndpoint + "/api/event/" + page + "/search");
}

export function getEventDetails(eventId) {
  return axios.get(semiEndpoint + "/api/event/" + eventId);
}
