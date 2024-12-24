//
//  LoginVC.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import Foundation
import UIKit

class LoginVC: UIViewController {
    
    //MARK: - Outlets & Variables -
        
    @IBOutlet weak var txtLoginEmail: UITextField!
    @IBOutlet weak var txtLoginPassword: UITextField!
    @IBOutlet weak var btnLogin: UIButton!
    @IBOutlet weak var txtSignupEmail: UITextField!
    @IBOutlet weak var txtSignupPassword: UITextField!
    @IBOutlet weak var btnSignup: UIButton!
    
    //MARK: - Lifecycle methods -
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    //MARK: - Custom methods -
    
    func setUp() {
        self.title = "Login"
    }
    
    func applyStyle() {
        self.view.setGradientWith(hex1: "2F9C3A",hex2: "C8F4CC")
    }
    
    //MARK: - Action methods -
    
    @IBAction func btnLoginTapped(_ sender: UIButton) {
        // Firebase Login here
    }
    
    @IBAction func btnSignupTapped(_ sender: UIButton) {
        // Firebase Signup here
    }
}

