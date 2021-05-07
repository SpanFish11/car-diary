import axios from "axios";
import authHeader from "@/services/auth-header";

export const AXIOS = axios.create({
  baseURL: `/api/v1/`,
  timeout: 1000,
  headers: authHeader(),
});
