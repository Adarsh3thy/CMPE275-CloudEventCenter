import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getSystemReport() {
  return axios.get(semiEndpoint + "/api/report/system");
}

export function getParticipantReport(userId) {
  return axios.get(semiEndpoint + "/api/report/user/" + userId);
}

export function getOrganizerReport(organizerId) {
  return axios.get(semiEndpoint + "/api/report/organizer/" + organizerId);
}
