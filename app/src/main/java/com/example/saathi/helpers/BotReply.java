package com.example.saathi.helpers;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;

public interface BotReply {

    void callback(DetectIntentResponse returnResponse);
}