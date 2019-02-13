/*
 * Copyright © Zhenjie Yan
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
package com.yanzhenjie.permission.runtime.setting;

import com.yanzhenjie.permission.Setting;
import com.yanzhenjie.permission.bridge.BridgeActivity;
import com.yanzhenjie.permission.bridge.BridgeRequest;
import com.yanzhenjie.permission.bridge.RequestManager;
import com.yanzhenjie.permission.source.Source;

/**
 * <p>Setting executor.</p>
 * Created by Zhenjie Yan on 2016/12/28.
 */
public class RuntimeSetting implements Setting, BridgeActivity.RequestListener {

    private Source mSource;
    private Setting.Action mComeback;

    public RuntimeSetting(Source source) {
        this.mSource = source;
    }

    @Override
    public void execute() {
        new RuntimeSettingPage(mSource).start(-1);
    }

    @Override
    public void execute(int requestCode) {
        new RuntimeSettingPage(mSource).start(requestCode);
    }

    @Override
    public void cancel() {
    }

    @Override
    public Setting onComeback(Setting.Action comeback) {
        this.mComeback = comeback;
        return this;
    }

    @Override
    public void start() {
        BridgeRequest request = new BridgeRequest(mSource.getContext());
        request.setType(BridgeRequest.TYPE_PERMISSION_SETTING);
        request.setListener(this);
        RequestManager.get().add(request);
    }

    @Override
    public void onRequestCallback() {
        if (mComeback != null) {
            mComeback.onAction();
        }
    }
}