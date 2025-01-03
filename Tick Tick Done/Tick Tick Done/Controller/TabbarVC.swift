//
//  TabbarVC.swift
//  Tick Tick Done
//
//  Created by CP on 24/12/24.
//

import Foundation
import UIKit

public class TabBarVC: UITabBarController {
    override public func viewDidLoad() {
        super.viewDidLoad()
        setupViewControllers()
    }

    func setupViewControllers() {
        let homeVC = STORYBOARD.instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
        homeVC.tabBarItem.image = UIImage(systemName: "house.fill")
        homeVC.tabBarItem.imageInsets = UIEdgeInsets(top: 5, left: 0, bottom: -5, right: 0);

        let profileVC = STORYBOARD.instantiateViewController(withIdentifier: "ProfileVC") as! ProfileVC
        profileVC.tabBarItem.image = UIImage(systemName: "person.fill")
        profileVC.tabBarItem.imageInsets = UIEdgeInsets(top: 5, left: 0, bottom: -5, right: 0);

        let addTaskVC = STORYBOARD.instantiateViewController(withIdentifier: "AddTaskVC") as! AddTaskVC
        addTaskVC.tabBarItem.image = UIImage(systemName: "plus.circle.fill")
        addTaskVC.tabBarItem.imageInsets = UIEdgeInsets(top: 5, left: 0, bottom: -5, right: 0);

        let historyVC = STORYBOARD.instantiateViewController(withIdentifier: "HistoryVC") as! HistoryVC
        historyVC.tabBarItem.image = UIImage(systemName: "list.bullet.rectangle.fill")
        historyVC.tabBarItem.imageInsets = UIEdgeInsets(top: 5, left: 0, bottom: -5, right: 0);

        let settingVC = STORYBOARD.instantiateViewController(withIdentifier: "SettingsVC") as! SettingsVC
        settingVC.tabBarItem.image = UIImage(systemName: "gearshape.fill")
        settingVC.tabBarItem.imageInsets = UIEdgeInsets(top: 5, left: 0, bottom: -5, right: 0);

        viewControllers = [homeVC, profileVC, addTaskVC, historyVC, settingVC]
    }
}
