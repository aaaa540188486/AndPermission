/*
 * Copyright 2018 Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.permission.overlay;

import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.bridge.BridgeActivity;
import com.yanzhenjie.permission.bridge.BridgeRequest;
import com.yanzhenjie.permission.bridge.RequestManager;
import com.yanzhenjie.permission.source.Source;

/**
 * Created by YanZhenjie on 2018/5/29.
 */
class LRequest extends BaseRequest implements RequestExecutor, BridgeActivity.RequestListener {

    private Source mSource;

    LRequest(Source source) {
        super(source);
        this.mSource = source;
    }

    @Override
    public void start() {
        if (tryDisplayDialog(mSource.getContext())) {
            callbackSucceed();
        } else {
            showRationale(this);
        }
    }

    @Override
    public void execute() {
        BridgeRequest request = new BridgeRequest(mSource.getContext());
        request.setType(BridgeRequest.TYPE_ALERT_WINDOW);
        request.setListener(this);
        RequestManager.get().add(request);
    }

    @Override
    public void cancel() {
        callbackFailed();
    }

    @Override
    public void onRequestCallback() {
        if (tryDisplayDialog(mSource.getContext())) {
            callbackSucceed();
        } else {
            callbackFailed();
        }
    }
}