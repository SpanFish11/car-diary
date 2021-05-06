import { AXIOS } from "@/http-common";

class AuthService {
  login(user) {
    return AXIOS.post("auth", {
      email: user.email,
      password: user.password,
    }).then((response) => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
        console.log();
      }
      return response.data;
    });
  }

  logout() {
    localStorage.removeItem("user");
  }
}

export default new AuthService();
