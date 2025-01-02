//
//  SceneDelegate.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import UIKit

class SceneDelegate: UIResponder, UIWindowSceneDelegate {

    var window: UIWindow?

    func scene(_ scene: UIScene, willConnectTo session: UISceneSession, options connectionOptions: UIScene.ConnectionOptions) {
        guard let _ = (scene as? UIWindowScene) else { return }
        self.setStartingScreen()
    }

    func setStartingScreen() {
        if(isUserLoggedIn) {
            self.setTabbar()
        } else {
            self.setLoginScreen()
        }
    }

    func setTabbar() {
        guard let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene else { return }
        let tabBarController = TabBarVC()
        self.window = UIWindow(windowScene: windowScene)
        self.window?.rootViewController = tabBarController
        self.window?.makeKeyAndVisible()
        window?.makeKeyAndVisible()
    }
    
    func setLoginScreen() {
        guard let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene else { return }
        let navVC = STORYBOARD.instantiateViewController(identifier: "NavVC") as! UINavigationController
        let loginVC = STORYBOARD.instantiateViewController(identifier: "LoginVC") as! LoginVC
        navVC.setViewControllers([loginVC], animated: true)
        self.window = UIWindow(windowScene: windowScene)
        window?.rootViewController = navVC
        window?.makeKeyAndVisible()
    }
}

