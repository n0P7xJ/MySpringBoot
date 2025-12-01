SendGrid / SMTP setup
----------------------

1. Create a SendGrid API key (Mail Send scope) in the SendGrid UI.
2. Set the API key in your environment before running the app:

```bash
export SENDGRID_API_KEY='SG.xxxxx'
export SENDGRID_MAIL_FROM='you@yourdomain.com' # verified sender
./mvnw spring-boot:run
```

3. If you previously committed an API key to the repo, rotate it in SendGrid immediately.

4. For local testing without sending real emails, consider Mailtrap or Ethereal and update `application.properties` accordingly.

Security notes:
- Never commit secrets to version control.
- Use environment variables or a secret manager.

Quick test steps:
- Register or create a user in the app (via `/users/register`).
- Go to `/forgot-password`, submit the user's email.
- Check SendGrid activity or the recipient inbox for the reset email.
- If you see SMTP errors, check application logs for the exception and verify the API key and `spring.mail.from` are correct and verified.
