import UIKit


protocol PriceBoardTableViewCellDelegate: AnyObject {
    func handlePressed(keyQuote: String) -> Void
}


class PriceBoardTableViewCell: UITableViewCell {
    @IBOutlet weak var containerImageView: UIView!
    @IBOutlet weak var containerSwipeableView: UIView!
    @IBOutlet weak var containerPriceStack: UIStackView!
    @IBOutlet weak var upDownImage: UIImageView!
    @IBOutlet weak var percentLabel: UILabel!
    @IBOutlet weak var pointLabel: UILabel!
    @IBOutlet weak var companyNameLabel: UILabel!
    @IBOutlet weak var symbolNameLabel: UILabel!
    @IBOutlet weak var tradePriceOverlayLabel: UILabel!
    @IBOutlet weak var tradePriceLabel: UILabel!
    @IBOutlet weak var containerView: UIView!
    
    let borderCenterItem = 2.0
    weak var delegate: PriceBoardTableViewCellDelegate? = nil
    var keyQuoteItem: String? = nil
}

extension PriceBoardTableViewCell {
    override func awakeFromNib() {
        super.awakeFromNib()
        setupView()
        setupGesture()
    }
    
    override func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return true
    }
}

extension PriceBoardTableViewCell {
    
    @objc private func handleTap() {
        delegate?.handlePressed(keyQuote: keyQuoteItem ?? "")
    }
    
    private func setupGesture() {
        let tapGesture      = UITapGestureRecognizer(target: self, action: #selector(handleTap))
        containerView.addGestureRecognizer(tapGesture)
    }
    
    private func moveContainerViewIntoPlace(nextX: CGFloat, completion: (()->Void)? = nil) {
        UIView.animate(withDuration: 0.2, animations: {
            self.containerView.frame = CGRect(x: nextX, y: self.containerView.frame.origin.y,
                                              width: self.containerView.bounds.size.width, height: self.containerView.bounds.size.height)
        }) { finished in
            if finished {
                completion?()
            }
        }
    }
    
    
    private func setupView() {
        // MARK: - Style
        selectionStyle                                  = .none
        containerView.layer.cornerRadius                = 8
        containerSwipeableView.layer.cornerRadius       = 8
        containerPriceStack.layer.cornerRadius          = 4
        tradePriceOverlayLabel.alpha                    = 0
        backgroundColor                                 = .clear
        companyNameLabel.alpha                          = 0.5
        // MARK: - Color
        symbolNameLabel.textColor                       = ColorName.textPrimary.color
        companyNameLabel.textColor                      = ColorName.textPrimary.color
        pointLabel.textColor                            = ColorName.textPrimary.color
        percentLabel.textColor                          = ColorName.textPrimary.color
        
        tradePriceLabel.textColor                       = ColorName.textPrimary.color
        tradePriceOverlayLabel.textColor                = ColorName.textPrimary.color
        containerView.backgroundColor                   = ColorName.backgroundSurfaces.color
        containerPriceStack.backgroundColor             = .clear
        // MARK: - Font
        symbolNameLabel.font                            = FontPreset.titleMedium.font
        companyNameLabel.font                           = FontPreset.labelLarge.font
        tradePriceLabel.font                            = FontPreset.labelLarge.font
        tradePriceOverlayLabel.font                     = FontPreset.labelLarge.font
        
        percentLabel.font                               = FontPreset.titleSmall.font
        pointLabel.font                                 = FontPreset.titleSmall.font
    }
}

extension PriceBoardTableViewCell {
    func animatedUpdateItem(item: Quote) {
        updateItemView(item: item)
        if item.tradePriceStatus != .UNKNOWN
        {
            switch item.tradePriceStatus {
            case .DOWN:
                containerPriceStack.backgroundColor      = ColorName.error.color
            case .UP:
                containerPriceStack.backgroundColor      = ColorName.success.color
                
            default:
                containerPriceStack.backgroundColor      = ColorName.textPrimary.color
            }
            tradePriceLabel.alpha                               = 0
            tradePriceOverlayLabel.alpha                        = 1
            
            UIView.animate(withDuration: 0.7) {
                self.containerPriceStack.backgroundColor        = .clear
                self.tradePriceLabel.alpha                      = 1
                self.tradePriceOverlayLabel.alpha               = 0
            }
        }
    }
    
    func updateItemView(item: Quote) {
        keyQuoteItem                    = item.key
        percentLabel.text               = item.percent
        pointLabel.text                 = item.point
        symbolNameLabel.text            = item.symbolName
        companyNameLabel.text           = item.companyName
        tradePriceLabel.text            = item.tradePrice
        tradePriceOverlayLabel.text     = item.tradePrice
        
        switch item.tradePriceStatus {
        case .DOWN:
            tradePriceLabel.textColor   = ColorName.error.color
        case .UP:
            tradePriceLabel.textColor   = ColorName.success.color
            
        default:
            tradePriceLabel.textColor   = ColorName.textPrimary.color
        }
        
        upDownImage.alpha               = item.pointStatus == .UNKNOWN ? 0 : 1
        containerImageView.isHidden     = item.pointStatus == .UNKNOWN
        
        switch item.pointStatus {
        case .DOWN:
            pointLabel.textColor        = ColorName.error.color
            upDownImage.image           = ImageName.down.image
        case .UP:
            pointLabel.textColor        = ColorName.success.color
            upDownImage.image           = ImageName.up.image
        default:
            pointLabel.textColor        = ColorName.textPrimary.color
        }
        
        switch item.percentStatus {
        case .DOWN:
            percentLabel.textColor      = ColorName.error.color
        case .UP:
            percentLabel.textColor      = ColorName.success.color
            
        default:
            percentLabel.textColor      = ColorName.textPrimary.color
        }
    }
}
