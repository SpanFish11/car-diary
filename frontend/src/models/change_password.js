export default class ChangePassword {
  constructor(oldPassword, newPassword, newPasswordConfirm) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
    this.newPasswordConfirm = newPasswordConfirm;
  }
}
