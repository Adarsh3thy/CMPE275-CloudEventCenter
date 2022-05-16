import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getEvents(page = 1) {
  return axios.get(semiEndpoint + "/api/event/" + page + "/search");
}

export function getEventDetails(eventId) {
  return axios.get(semiEndpoint + "/api/event/" + eventId);
}

export function createEvent(dataJson) {
  return axios.post(semiEndpoint + "/api/event", dataJson);
}

export function registerEvent(eventId, userId) {
  return axios.put(
    semiEndpoint + "/api/registration/" + eventId + "/" + userId
  );
}

export function getEventRegistrationsByParticipant(userId) {
  return axios.get(semiEndpoint + "/api/auth/event/" + userId);
}

export function getEventRegistrationsByOrganizer(userId) {
  return axios.get(semiEndpoint + "/api/event/organizer/" + userId);
}
