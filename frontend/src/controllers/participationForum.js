import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getQuestionsByEvent(eventId, userId) {
  return axios.get(
    semiEndpoint + "/api/forums/participant/" + eventId + "/questions" + "?userId=" + userId
  );
}

export function createQuestion(eventId, userId, dataJson) {
  return axios.post(
    semiEndpoint +
      "/api/forums/participant/" +
      eventId +
      "/questions?text=" +
      dataJson.text +
      "&userId=" + 
      userId
  );
}

export function createAnswer(questionId, dataJson) {
  return axios.post(
    semiEndpoint +
      "/api/forums/participant/questions/" +
      questionId +
      "/answers?text=" +
      dataJson.text +
      "&userId=" +
      dataJson.userId
  );
}

export function getQuestionAnswers(questionId, userId) {
  return axios.get(
    semiEndpoint + "/api/forums/participant/questions/" + questionId + "/answers" + "?userId=" + userId
  );
}

export function closeForum(userId, eventId, text) {
  return axios.post(
    `${semiEndpoint}/api/forums/participant/${eventId}/close?text=${text}&userId=${userId}`
  )
};

export function canPostToForum(event) {
  if (!event) return false;
  const {pForumOpen, endTime} = event;

  if (pForumOpen === false)
    return false;

  let eventEndTime = new Date(endTime);
  eventEndTime.setTime(eventEndTime.getTime() + (1000 * 60 * 60 * 24 * 3));

  if (new Date().getTime() > eventEndTime) 
    return false;

  return true;
};