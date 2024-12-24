//
//  LaunchVC.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import Foundation
import UIKit

class LaunchVC: UIViewController {
    
    //MARK: - Outlets & Variables -
    
    @IBOutlet weak var lblTick: UILabel!
    @IBOutlet weak var viewProgress: CircularProgressView!
    private var timer = Timer()
    private var tickCount = 0
    private var colorAlpha: CGFloat = 0.25
    private var isProgressStarted = false
    
    //MARK: - Lifecycle methods -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.setUp()
        self.applyStyle()
    }
    
    //MARK: - Custom methods -
    
    func setUp() {
        self.lblTick.text = "TICK"
        self.lblTick.textColor = Utility.shared.getColorFrom(hexString: "08841E").withAlphaComponent(self.colorAlpha)
        self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.update), userInfo: nil, repeats: true)
        
        self.viewProgress.setProgressColor = Utility.shared.getColorFrom(hexString: "08841E")
        self.viewProgress.setTrackColor = Utility.shared.getColorFrom(hexString: "D0DAD2")
    }
    
    func applyStyle() {
        self.view.setGradientWith(hex1: "2F9C3A",hex2: "C8F4CC")
        self.lblTick.font = UIFont.boldSystemFont(ofSize: 30)
    }
    
    @objc func update() {
        if(!self.isProgressStarted) {
            self.viewProgress.setProgressWithAnimation(duration: 2.5, value: 1)
        }
        self.isProgressStarted = true
        self.tickCount += 1
        self.lblTick.text = self.tickCount > 2 ? "DONE" : "TICK"
        self.lblTick.textColor = Utility.shared.getColorFrom(hexString: "08841E").withAlphaComponent(self.colorAlpha)
        self.colorAlpha += 0.25
        if(self.tickCount > 2) {
            self.timer.invalidate()
            self.timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.navigate), userInfo: nil, repeats: true)
        }
    }
    
    @objc func navigate() {
        self.timer.invalidate()
        SCENE_DELEGATE.setStartingScreen()
    }
}
