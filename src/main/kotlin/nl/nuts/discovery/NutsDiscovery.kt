/*
 *     Nuts discovery service for Corda network creation
 *     Copyright (C) 2019 Nuts community
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package nl.nuts.discovery

import net.corda.core.serialization.internal.SerializationEnvironmentImpl
import net.corda.core.serialization.internal.nodeSerializationEnv
import net.corda.nodeapi.internal.serialization.AMQP_P2P_CONTEXT
import net.corda.nodeapi.internal.serialization.SerializationFactoryImpl
import net.corda.nodeapi.internal.serialization.amqp.AMQPClientSerializationScheme
import net.corda.nodeapi.internal.serialization.amqp.AMQPServerSerializationScheme
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@EnableConfigurationProperties
@SpringBootApplication
class NutsDiscovery {


    /**
     * Contains Corda magic to enable Object serialization
     */
    @PostConstruct
    fun init() {
        if (nodeSerializationEnv == null) {
            nodeSerializationEnv = SerializationEnvironmentImpl(
                    SerializationFactoryImpl().apply {
                        registerScheme(AMQPServerSerializationScheme(emptyList()))
                    },
                    AMQP_P2P_CONTEXT
            )
        }
    }
}

fun main(args: Array<String>) {
    runApplication<NutsDiscovery>(*args)
}
