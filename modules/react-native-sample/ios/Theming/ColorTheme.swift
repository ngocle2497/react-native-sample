import Foundation
import UIKit

enum ColorName: String {
    case attention
    case error
    case success
    case info
    case primary
    case background
    case backgroundSurfaces
    case textPrimary
    case textPrimaryBody
    case textSecondaryBody
    case border
    case warning
    
    var color: UIColor {
        return (ThemeManager.colors[self.rawValue] ?? .clear) ?? .clear
    }
}

extension UIColor {
    convenience init(_ red: CGFloat,_ green: CGFloat,_ blue: CGFloat,_ alpha: CGFloat) {
        self.init(red: red / 255.5, green: green / 255.0, blue: blue / 255.0, alpha: alpha)
    }
    
    convenience init(rgba: String) {
        if rgba.contains("rgba") {
            let colors = rgba.replacingOccurrences(of: "rgba", with: "").replacingOccurrences(of: "(", with: "").replacingOccurrences(of: ")", with: "").components(separatedBy: ",")
            let red         = Double(colors[0]).map{ CGFloat($0) } ?? 0.0
            let green       = Double(colors[1]).map{ CGFloat($0) } ?? 0.0
            let blue        = Double(colors[2]).map{ CGFloat($0) } ?? 0.0
            let alpha       = Double(colors[3]).map{ CGFloat($0) } ?? 0.0
            self.init(red: red / 255.5, green: green / 255.0, blue: blue / 255.0, alpha: alpha)
        } else if(rgba.contains("rgb")) {
            let colors = rgba.replacingOccurrences(of: "rgba", with: "").replacingOccurrences(of: "(", with: "").replacingOccurrences(of: ")", with: "").components(separatedBy: ",")
            let red         = Double(colors[0]).map{ CGFloat($0) } ?? 0.0
            let green       = Double(colors[1]).map{ CGFloat($0) } ?? 0.0
            let blue        = Double(colors[2]).map{ CGFloat($0) } ?? 0.0
            
            self.init(red: red / 255.5, green: green / 255.0, blue: blue / 255.0, alpha: 1.0)
            
        } else {
            self.init(red: 0, green: 0, blue: 0, alpha: 0)
        }
    }
    
    convenience init(hex: String) {
        var cString:String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        
        if (cString.hasPrefix("#")) {
            cString.remove(at: cString.startIndex)
        }
        var r: CGFloat = 0.0
        var g: CGFloat = 0.0
        var b: CGFloat = 0.0
        var a: CGFloat = 1.0
        
        var rgbValue:UInt64 = 0
        Scanner(string: cString).scanHexInt64(&rgbValue)
        
        if ((cString.count) == 8) {
            r = CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0
            g =  CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0
            b = CGFloat((rgbValue & 0x0000FF)) / 255.0
            a = CGFloat((rgbValue & 0xFF000000)  >> 24) / 255.0
            
        }else if ((cString.count) == 6){
            r = CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0
            g =  CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0
            b = CGFloat((rgbValue & 0x0000FF)) / 255.0
            a =  CGFloat(1.0)
        }
        
        
        self.init(  red: r,
                    green: g,
                    blue: b,
                    alpha: a
        )
    }
}
