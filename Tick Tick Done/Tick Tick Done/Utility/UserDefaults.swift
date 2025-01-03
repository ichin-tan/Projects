//
//  UserDefaults.swift
//  Tick Tick Done
//
//  Created by CP on 24/12/24.
//

import Foundation

var isUserLoggedIn: Bool {
    set {
        USER_DEFAULTS.setValue(newValue, forKey: "isUserLoggedIn")
    }
    get {
        return USER_DEFAULTS.value(forKey: "isUserLoggedIn") as? Bool ?? false
    }
}
