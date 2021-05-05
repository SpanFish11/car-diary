import axios from "axios";

export const AXIOS = axios.create({
  baseURL: `/api/v1/`,
  timeout: 1000,
  headers: {
    "Content-type": "application/json",
  },
});
