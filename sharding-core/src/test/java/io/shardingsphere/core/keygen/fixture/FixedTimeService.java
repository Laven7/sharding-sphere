/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.core.keygen.fixture;

import io.shardingsphere.core.keygen.SnowflakeKeyGenerator;
import io.shardingsphere.core.keygen.TimeService;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public final class FixedTimeService extends TimeService {
    
    private final int expectedInvokedTimes;
    
    private final AtomicInteger invokedTimes = new AtomicInteger();
    
    private long current = SnowflakeKeyGenerator.EPOCH;
    
    @Override
    public long getCurrentMillis() {
        if (invokedTimes.getAndIncrement() < expectedInvokedTimes) {
            return current;
        }
        invokedTimes.set(0);
        return ++current;
    }
}
