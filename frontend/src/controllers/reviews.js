import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function createOrganizerReview(eventId, reviewerId, dataJson) {
  return axios.post(
    semiEndpoint + "/api/reviews/" + eventId + "/organizer" + reviewerId,
    dataJson
  );
}

export function createParticipantReview(eventId, participantId, dataJson) {
  return axios.post(
    semiEndpoint + "/api/reviews/" + eventId + "/participant" + participantId,
    dataJson
  );
}

export function getAverageParticipantRatings(participantId) {
  return axios.get(
    semiEndpoint + "/api/reviews/participant/ratings/" + participantId
  );
}

export function getAverageOrganizerRatings(organizerId) {
  return axios.get(
    semiEndpoint + "/api/reviews/organizer/ratings/" + organizerId
  );
}

export function getParticipantReviews(participantId) {
  return axios.get(
    semiEndpoint + "/api/reviews/participant/reviews/" + participantId
  );
}

export function getOrganizerReviews(organizerId) {
  return axios.get(
    semiEndpoint + "/api/reviews/organizer/reviews/" + organizerId
  );
}
