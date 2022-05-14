import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getQuestionsByEvent(eventId) {
  return axios.get(
    semiEndpoint + "/api/forums/sign_up/" + eventId + "/questions"
  );
}

export function createQuestion(eventId, dataJson) {
  return axios.put(
    semiEndpoint +
      "/api/forums/sign_up/" +
      eventId +
      "/questions?text=" +
      dataJson.text +
      "&userId=" +
      dataJson.userId
  );
}

export function getQuestionAnswers(questionId) {
  return axios.get(
    semiEndpoint + "/api/forums/sign_up/questions/" + questionId + "/answers"
  );
}
