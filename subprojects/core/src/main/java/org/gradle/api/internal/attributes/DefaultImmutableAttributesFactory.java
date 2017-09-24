/*
 * Copyright 2017 the original author or authors.
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
package org.gradle.api.internal.attributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.gradle.api.attributes.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultImmutableAttributesFactory implements ImmutableAttributesFactory {
    private final ImmutableAttributes root;
    private final Map<ImmutableAttributes, List<ImmutableAttributes>> children;

    public DefaultImmutableAttributesFactory() {
        this.root = new ImmutableAttributes();
        this.children = Maps.newHashMap();
        children.put(root, new ArrayList<ImmutableAttributes>());
    }

    public int size() {
        return children.size();
    }

    @Override
    public <T> ImmutableAttributes of(Attribute<T> key, T value) {
        return concat(root, key, value);
    }

    @Override
    public synchronized <T> ImmutableAttributes concat(ImmutableAttributes node, Attribute<T> key, T value) {
        List<ImmutableAttributes> nodeChildren = children.get(node);
        if (nodeChildren == null) {
            nodeChildren = Lists.newArrayList();
            children.put(node, nodeChildren);
        }
        for (ImmutableAttributes child : nodeChildren) {
            if (child.attribute.equals(key) && child.value.equals(value)) {
                return child;
            }
        }
        ImmutableAttributes child = new ImmutableAttributes(node, key, value);
        nodeChildren.add(child);
        return child;
    }

    public ImmutableAttributes getRoot() {
        return root;
    }

    @Override
    public ImmutableAttributes concat(ImmutableAttributes attributes1, ImmutableAttributes attributes2) {
        ImmutableAttributes current = attributes2;
        for (Attribute attribute : attributes1.keySet()) {
            if (!current.contains(attribute)) {
                current = concat(current, attribute, attributes1.getAttribute(attribute));
            }
        }
        return current;
    }
}
