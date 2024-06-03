import UIKit

protocol EndlessCollectionViewCellDelegate: AnyObject {
    func handlePressed(keyQuote: String) -> Void
}


class EndlessCollectionViewCell: UICollectionViewCell {
    
    @IBOutlet weak var tradePriceOverlayLabel: UILabel!
    @IBOutlet weak var containerTradePriceView: UIView!
    @IBOutlet weak var percentLabel: UILabel!
    @IBOutlet weak var poinLabel: UILabel!
    @IBOutlet weak var tradePriceLabel: UILabel!
    @IBOutlet weak var symbolNameLabel: UILabel!
    @IBOutlet weak var containerView: UIView!
    weak var delegate: EndlessCollectionViewCellDelegate? = nil
    private var keyQuote: String = ""
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setup()
        setupGesture()
    }
    
    
}

extension EndlessCollectionViewCell {
    private func setup() {
        // MARK: - Style
        containerTradePriceView.layer.cornerRadius      = 4
        containerView.layer.cornerRadius                = 8
        containerView.layer.borderWidth                 = 1
        tradePriceOverlayLabel.alpha                    = 0
        // MARK: - Color
        containerView.layer.borderColor                 = ColorName.border.color.cgColor
        containerView.backgroundColor                   = ColorName.backgroundSurfaces.color
        symbolNameLabel.textColor                       = ColorName.textPrimary.color
        tradePriceLabel.textColor                       = ColorName.textPrimary.color
        tradePriceOverlayLabel.textColor                = ColorName.textPrimary.color
        poinLabel.textColor                             = ColorName.textPrimary.color
        percentLabel.textColor                          = ColorName.textPrimary.color
        // MARK: - Font
        symbolNameLabel.font                            = FontPreset.titleMedium.font
        tradePriceLabel.font                            = FontPreset.labelLarge.font
        tradePriceOverlayLabel.font                     = FontPreset.labelLarge.font
        poinLabel.font                                  = FontPreset.titleSmall.font
        percentLabel.font                               = FontPreset.titleSmall.font
    }
    
    private func setupGesture() {
        let tapGesture      = UITapGestureRecognizer(target: self, action: #selector(handleTap))
        containerView.addGestureRecognizer(tapGesture)
    }
    
    @objc private func handleTap() {
        delegate?.handlePressed(keyQuote: keyQuote)
    }
}

extension EndlessCollectionViewCell {
    func updateItemView(item: EndlessQuote) {
        keyQuote                                        = item.key
        symbolNameLabel.text                            = item.symbolName
        poinLabel.text                                  = item.point
        percentLabel.text                               = item.percent
        tradePriceLabel.text                            = item.tradePrice
        tradePriceOverlayLabel.text                     = item.tradePrice
        
        
        switch item.tradePriceStatus {
        case .UP:
            tradePriceLabel.textColor = ColorName.success.color
            break
        case .DOWN:
            tradePriceLabel.textColor = ColorName.error.color
            break
        default:
            tradePriceLabel.textColor = ColorName.textPrimary.color
            break
        }
        switch item.pointStatus {
        case .UP:
            poinLabel.textColor                         = ColorName.success.color
            break
        case .DOWN:
            poinLabel.textColor                         = ColorName.error.color
            break
        default:
            poinLabel.textColor                         = ColorName.textPrimary.color
            break
        }
        
        switch item.percentStatus {
        case .UP:
            percentLabel.textColor = ColorName.success.color
            break
        case .DOWN:
            percentLabel.textColor = ColorName.error.color
            break
        default:
            percentLabel.textColor = ColorName.textPrimary.color
            break
        }
    }
    
    func animatePrice(item: EndlessQuote){
        updateItemView(item: item)
        if item.tradePriceStatus != .UNKNOWN{
            switch item.tradePriceStatus {
            case .UP:
                containerTradePriceView.backgroundColor = ColorName.success.color
                break
            case .DOWN:
                containerTradePriceView.backgroundColor = ColorName.error.color
                break
            default:
                containerTradePriceView.backgroundColor = ColorName.textPrimary.color
                break
            }
            tradePriceLabel.alpha                                   = 0
            tradePriceOverlayLabel.alpha                            = 1
            
            UIView.animate(withDuration: 0.7) {
                self.containerTradePriceView.backgroundColor        = .clear
                self.tradePriceLabel.alpha                          = 1
                self.tradePriceOverlayLabel.alpha                   = 0
            }
        }
        
    }
}
