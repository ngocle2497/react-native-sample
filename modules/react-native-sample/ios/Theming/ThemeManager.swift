import Foundation
import UIKit


struct ThemeManager {
    
    static var fontPrimaryBold: String               = "PoppinsLatin-Bold"
    static var fontPrimary: String                   = "PoppinsLatin-Regular"
    static var dicIconImage: [String: UIImage] = [:]

    static var colors: [String: UIColor?]            = [:]
    
    static var fonts: [String: UIFont?]              = [:]
    
    static func updateColorDic(dic: NSDictionary) {
        for (key, value) in dic {
            if let key = key as? String, let value = value as? NSNumber {
                colors[key] = RCTConvert.uiColor(value.intValue)
            }
        }
    }
    
    static func updateImagesDic(dic: NSDictionary, completion: @escaping () -> Void) {
        let dispatchGroup = DispatchGroup()
        
        for (key, value) in dic {
            if let key = key as? String, let value = value as? String {
                downloadLocalImage(uri: value, group: dispatchGroup) { $0(&dicIconImage, key) }
            }
        }
        
        dispatchGroup.notify(queue: .main) {
            print("Preload Icon Image Done!")
            
            completion()
        }
    }

    static func updateFontDic(dic: NSDictionary) {
        for (key, value) in dic {
            if let key = key as? String, let value = value as? NSDictionary {
                if let oldValue = fonts[key] {
                    let fontFamily = value["fontFamily"] as? String
                    let fontSize = value["fontSize"] as? NSNumber
                    let font = RCTConvert.uiFont(["fontFamily": fontFamily ?? fontPrimary, "fontWeight": "normal", "fontSize": RCTConvert.cgFloat(fontSize?.doubleValue ?? oldValue?.pointSize ?? 0.0)])
                    fonts[key] = font
                }
            }
        }
    }
    private static func downloadLocalImage(uri: String?, group: DispatchGroup, completion: @escaping ((inout [String: UIImage], String) -> Void )-> Void){
        if let url = URL(string: uri ?? "") {
            group.enter()
            URLSession.shared.dataTask(with: url , completionHandler: { data, response, error in
                
                guard let data = data else {
                    group.leave()
                    return
                }
                completion { v, k in
                    v[k] = UIImage(data: data)
                    group.leave()
                }
            }).resume()
        }
        
    }
}
