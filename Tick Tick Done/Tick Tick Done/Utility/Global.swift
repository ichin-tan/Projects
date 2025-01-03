//
//  Global.swift
//  Tick Tick Done
//
//  Created by CP on 30/12/24.
//

import Foundation
import UIKit

class Global {
    
    static func setMainBackground(for vcView: UIView) {
        vcView.setGradientWith(hex1: "AFF9B7",hex2: "EEFAF0")
    }
    
    static func isValidEmail(_ email: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        return emailPred.evaluate(with: email)
    }

    struct AppMessages {
        static let somethingWentWrong = "Something went wrong"
        static let invalidCred = "No credentials found"
        static let enterEmail = "Please enter email"
        static let enterValidEmail = "Please enter valid email"
        static let enterPassword = "Please enter password"
        static let done = "DONE"
        static let tick = "TICK"
        static let login = "Login"
        static let signup = "Signup"
        static let userCreated = "User created successfully"
    }
    
    class FirebaseErrors {
        static let invalidCred = "The supplied auth credential is malformed or has expired."
    }
}

