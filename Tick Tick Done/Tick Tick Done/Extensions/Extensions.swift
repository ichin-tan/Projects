//
//  Extensions.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import Foundation
import UIKit

// MARK: - UIView Extension

extension UIView {
    func setGradientWith(hex1: String = "", hex2: String = "",  locations: [NSNumber] = [0.0, 1.0]) {
        let gradientLayer = CAGradientLayer()
        gradientLayer.frame = self.bounds
        
        let color1 = Utility.shared.getColorFrom(hexString: hex1).cgColor
        let color2 = Utility.shared.getColorFrom(hexString: hex2).cgColor

        gradientLayer.colors = [color1, color2]
        gradientLayer.locations = locations
        self.layer.insertSublayer(gradientLayer, at: 0)
    }
    
    func setGradientWith(color1: UIColor, color2: UIColor, locations: [NSNumber] = [0.0, 1.0]) {
        let gradientLayer = CAGradientLayer()
        gradientLayer.frame = self.bounds
        gradientLayer.colors = [color1.cgColor, color2.cgColor]
        gradientLayer.locations = locations
        self.layer.insertSublayer(gradientLayer, at: 0)

    }
}
