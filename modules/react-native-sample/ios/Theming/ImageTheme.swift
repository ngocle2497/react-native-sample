import UIKit

enum ImageName: String {
    case up
    case down
    
    var image: UIImage? {
        return ThemeManager.dicIconImage[self.rawValue]
    }
}
