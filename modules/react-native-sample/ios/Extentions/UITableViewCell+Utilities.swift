
extension UITableViewCell {
    static var identifier: String {
        return String(describing: self)
    }
    
    static var uiNib: UINib {
        return UINib(nibName: self.identifier, bundle: nil)
    }
}
