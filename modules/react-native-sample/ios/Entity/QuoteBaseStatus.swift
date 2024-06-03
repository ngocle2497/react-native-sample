import Foundation

enum QuoteBaseStatus: String {
    case UP = "UP"
    case DOWN = "DOWN"
    case UNKNOWN = "UNKNOWN"
    
    static func fromString(value: String) -> Self{
        switch(value) {
        case "UP":
            return .UP
        case "DOWN":
            return .DOWN
        default:
            return .UNKNOWN
        }
    }
}
