package com.SlotSync.SlotSync.UtilitiesFile;

public class Data {
  public static String getMessageBody(String otp) {
    String htmlContent = """
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Your OTP Code</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                        background-color: #ffffff;
                        border-radius: 8px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        text-align: center;
                        padding-bottom: 20px;
                        border-bottom: 1px solid #dddddd;
                    }
                    .header h1 {
                        font-size: 24px;
                        color: #333333;
                        margin: 0;
                    }
                    .content {
                        padding: 20px 0;
                    }
                    .content p {
                        font-size: 16px;
                        line-height: 1.5;
                        color: #555555;
                    }
                    .otp-code {
                        font-size: 36px;
                        font-weight: bold;
                        color: #1a73e8;
                        text-align: center;
                        margin: 20px 0;
                    }
                    .footer {
                        text-align: center;
                        padding-top: 20px;
                        border-top: 1px solid #dddddd;
                        font-size: 12px;
                        color: #888888;
                    }
                    .footer p {
                        margin: 5px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>SlotSync</h1>
                    </div>
                    <div class="content">
                        <p>Hello,</p>
                        <p>Your One-Time Password (OTP) for account verification is:</p>
                        <div class="otp-code">%s</div>
                        <p>This OTP is valid for a limited time. Please do not share it with anyone.</p>
                        <p>If you did not request this, please ignore this email.</p>
                    </div>
                    <div class="footer">
                        <p>By using this service, you agree to our Terms and Conditions.</p>
                        <p>This is a system-generated email, please do not reply.</p>
                        <p><b>Satyam Soni, Owner</b></p>
                    </div>
                </div>
            </body>
            </html>
            """;
    return htmlContent;
  }
  
}
