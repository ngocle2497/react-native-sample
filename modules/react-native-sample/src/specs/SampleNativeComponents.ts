import {Platform, UIManager, requireNativeComponent} from 'react-native';
import {NativePriceBoardListViewProps} from '../types/PriceBoardListViewProps';

const LINKING_ERROR =
  "The package 'react-native-sample' doesn't seem to be linked. Make sure: \n\n" +
  Platform.select({ios: "- You have run 'pod install'\n", default: ''}) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const PriceBoardListViewComponentName = 'PriceBoardListView';

export const NativePriceBoardListView =
  UIManager.getViewManagerConfig(PriceBoardListViewComponentName) != null
    ? requireNativeComponent<NativePriceBoardListViewProps>(
        PriceBoardListViewComponentName,
      )
    : () => {
        throw new Error(LINKING_ERROR);
      };

const HeaderListViewComponentName = 'HeaderWrapperView';

export const NativeHeaderWrapperView =
  UIManager.getViewManagerConfig(HeaderListViewComponentName) != null
    ? requireNativeComponent<{}>(HeaderListViewComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };

const FooterListViewComponentName = 'FooterWrapperView';

export const NativeFooterWrapperView =
  UIManager.getViewManagerConfig(FooterListViewComponentName) != null
    ? requireNativeComponent<{}>(FooterListViewComponentName)
    : () => {
        throw new Error(LINKING_ERROR);
      };
