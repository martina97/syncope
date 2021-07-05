/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.client.console.commons;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.syncope.client.console.rest.PolicyRestClient;
import org.apache.syncope.client.console.wicket.markup.html.form.PolicyRenderer;
import org.apache.syncope.client.ui.commons.markup.html.form.AjaxDropDownChoicePanel;
import org.apache.syncope.common.lib.policy.PolicyTO;
import org.apache.syncope.common.lib.to.RealmTO;
import org.apache.syncope.common.lib.types.PolicyType;
import org.apache.wicket.markup.html.form.AbstractSingleSelectChoice;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

public class AMRealmPolicyProvider extends IdRepoRealmPolicyProvider {

    private static final long serialVersionUID = 1671878489700L;

    private final IModel<Map<String, String>> accessPolicies = new LoadableDetachableModel<Map<String, String>>() {

        private static final long serialVersionUID = -2012833443695917883L;

        @Override
        protected Map<String, String> load() {
            return PolicyRestClient.list(PolicyType.ACCESS).stream().
                    collect(Collectors.toMap(PolicyTO::getKey, PolicyTO::getName));
        }
    };

    private final IModel<Map<String, String>> attrReleasePolicies = new LoadableDetachableModel<Map<String, String>>() {

        private static final long serialVersionUID = -2012833443695917883L;

        @Override
        protected Map<String, String> load() {
            return PolicyRestClient.list(PolicyType.ATTR_RELEASE).stream().
                    collect(Collectors.toMap(PolicyTO::getKey, PolicyTO::getName));
        }
    };

    private final IModel<Map<String, String>> authPolicies = new LoadableDetachableModel<Map<String, String>>() {

        private static final long serialVersionUID = -2012833443695917883L;

        @Override
        protected Map<String, String> load() {
            return PolicyRestClient.list(PolicyType.AUTH).stream().
                    collect(Collectors.toMap(PolicyTO::getKey, PolicyTO::getName));
        }
    };

    @Override
    public void add(final RealmTO realmTO, final RepeatingView view) {
        super.add(realmTO, view);

        AjaxDropDownChoicePanel<String> accessPolicy = new AjaxDropDownChoicePanel<>(
                view.newChildId(),
                new ResourceModel("accessPolicy", "accessPolicy").getObject(),
                new PropertyModel<>(realmTO, "accessPolicy"),
                false);
        accessPolicy.setChoiceRenderer(new PolicyRenderer(accessPolicies));
        accessPolicy.setChoices(new ArrayList<>(accessPolicies.getObject().keySet()));
        ((AbstractSingleSelectChoice<?>) accessPolicy.getField()).setNullValid(true);
        view.add(accessPolicy);

        AjaxDropDownChoicePanel<String> attrReleasePolicy = new AjaxDropDownChoicePanel<>(
                view.newChildId(),
                new ResourceModel("attrReleasePolicy", "attrReleasePolicy").getObject(),
                new PropertyModel<>(realmTO, "attrReleasePolicy"),
                false);
        attrReleasePolicy.setChoiceRenderer(new PolicyRenderer(attrReleasePolicies));
        attrReleasePolicy.setChoices(new ArrayList<>(attrReleasePolicies.getObject().keySet()));
        ((AbstractSingleSelectChoice<?>) attrReleasePolicy.getField()).setNullValid(true);
        view.add(attrReleasePolicy);

        AjaxDropDownChoicePanel<String> authPolicy = new AjaxDropDownChoicePanel<>(
                view.newChildId(),
                new ResourceModel("authPolicy", "authPolicy").getObject(),
                new PropertyModel<>(realmTO, "authPolicy"),
                false);
        authPolicy.setChoiceRenderer(new PolicyRenderer(authPolicies));
        authPolicy.setChoices(new ArrayList<>(authPolicies.getObject().keySet()));
        ((AbstractSingleSelectChoice<?>) authPolicy.getField()).setNullValid(true);
        view.add(authPolicy);
    }
}
