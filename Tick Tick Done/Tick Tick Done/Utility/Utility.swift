//
//  Utility.swift
//  Tick Tick Done
//
//  Created by CP on 19/12/24.
//

import Foundation
import UIKit

class Utility {
    
    static let shared = Utility()
    
    func getColorFrom(hexString: String) -> UIColor {
        var hexSanitized = hexString.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        
        if hexSanitized.hasPrefix("#") {
            hexSanitized.removeFirst()
        }
        
        guard hexSanitized.count == 6 else { return UIColor.white }
        
        var rgb: UInt64 = 0
        Scanner(string: hexSanitized).scanHexInt64(&rgb)
        
        let red = CGFloat((rgb & 0xFF0000) >> 16) / 255.0
        let green = CGFloat((rgb & 0x00FF00) >> 8) / 255.0
        let blue = CGFloat(rgb & 0x0000FF) / 255.0
        
        return UIColor(red: red, green: green, blue: blue, alpha: 1.0)
    }
    
}
