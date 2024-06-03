import Foundation
import UIKit

enum FontPreset: String {
    case headlineLarge
    case headlineMedium
    case headlineSmall

    case titleLarge
    case titleMedium
    case titleSmall

    case labelLarge
    case labelMedium
    case labelSmall

    case bodyLarge
    case bodyMedium
    case bodySmall
    
    var font: UIFont? {
        return ThemeManager.fonts[self.rawValue] ?? nil
    }
}
