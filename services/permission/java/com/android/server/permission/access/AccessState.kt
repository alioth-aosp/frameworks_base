/*
 * Copyright (C) 2021 The Android Open Source Project
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

package com.android.server.permission.access

import android.content.pm.PermissionGroupInfo
import com.android.server.permission.access.collection.* // ktlint-disable no-wildcard-imports
import com.android.server.permission.access.permission.Permission
import com.android.server.pm.permission.PermissionAllowlist
import com.android.server.pm.pkg.PackageState

class AccessState private constructor(
    val systemState: SystemState,
    val userStates: IntMap<UserState>
) {
    constructor() : this(
        SystemState(),
        IntMap()
    )

    fun copy(): AccessState = AccessState(
        systemState.copy(),
        userStates.copy { it.copy() }
    )
}

class SystemState private constructor(
    val userIds: IntSet,
    var packageStates: Map<String, PackageState>,
    var disabledSystemPackageStates: Map<String, PackageState>,
    val appIds: IntMap<IndexedListSet<String>>,
    // A map of KnownPackagesInt to a set of known package names
    val knownPackages: IntMap<IndexedListSet<String>>,
    // A map of userId to packageName
    val deviceAndProfileOwners: IntMap<String>,
    // Whether the device supports leanback UI
    var isLeanback: Boolean,
    val privilegedPermissionAllowlistSourcePackageNames: IndexedListSet<String>,
    var permissionAllowlist: PermissionAllowlist,
    val implicitToSourcePermissions: Map<String, Set<String>>,
    val permissionGroups: IndexedMap<String, PermissionGroupInfo>,
    val permissionTrees: IndexedMap<String, Permission>,
    val permissions: IndexedMap<String, Permission>
) : WritableState() {
    constructor() : this(
        IntSet(),
        emptyMap(),
        emptyMap(),
        IntMap(),
        IntMap(),
        IntMap(),
        false,
        IndexedListSet(),
        PermissionAllowlist(),
        IndexedMap(),
        IndexedMap(),
        IndexedMap(),
        IndexedMap()
    )

    fun copy(): SystemState =
        SystemState(
            userIds.copy(),
            packageStates,
            disabledSystemPackageStates,
            appIds.copy { it.copy() },
            knownPackages.copy { it.copy() },
            deviceAndProfileOwners.copy { it },
            isLeanback,
            privilegedPermissionAllowlistSourcePackageNames.copy(),
            permissionAllowlist,
            implicitToSourcePermissions,
            permissionGroups.copy { it },
            permissionTrees.copy { it },
            permissions.copy { it }
        )
}

class UserState private constructor(
    // A map of (appId to a map of (permissionName to permissionFlags))
    val uidPermissionFlags: IntMap<IndexedMap<String, Int>>,
    val uidAppOpModes: IntMap<IndexedMap<String, Int>>,
    val packageAppOpModes: IndexedMap<String, IndexedMap<String, Int>>
) : WritableState() {
    constructor() : this(
        IntMap(),
        IntMap(),
        IndexedMap()
    )

    fun copy(): UserState = UserState(
        uidPermissionFlags.copy { it.copy { it } },
        uidAppOpModes.copy { it.copy { it } },
        packageAppOpModes.copy { it.copy { it } }
    )
}

object WriteMode {
    const val NONE = 0
    const val SYNC = 1
    const val ASYNC = 2
}

abstract class WritableState {
    var writeMode: Int = WriteMode.NONE
        private set

    fun requestWrite(sync: Boolean = false) {
        if (sync) {
            writeMode = WriteMode.SYNC
        } else {
            if (writeMode != WriteMode.SYNC) {
                writeMode = WriteMode.ASYNC
            }
        }
    }
}

class GetStateScope(
    val state: AccessState
)

class MutateStateScope(
    val oldState: AccessState,
    val newState: AccessState
)
