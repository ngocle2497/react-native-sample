import {Image} from 'react-native';
import {setTheme} from 'react-native-sample';

const colors = {
  attention: '#FACC15',
  warning: '#F59E0B',
  error: '#EF4444',
  success: '#3BB266',
  info: '#1D2939',
  primary: '#613DE4',
  background: '#1D2939',
  backgroundSurfaces: '#101828',
  textPrimary: '#FFFFFF',
  textPrimaryBody: '#FFFFFF',
  textSecondaryBody: '#9CA3AF',
  border: '#475467',
};

const textPresets = {
  headlineLarge: {
    fontFamily: 'Poppins-Bold',
    fontSize: 32,
  },
  headlineMedium: {
    fontFamily: 'Poppins-Bold',
    fontSize: 28,
  },
  headlineSmall: {
    fontFamily: 'Poppins-Bold',
    fontSize: 24,
  },

  titleLarge: {
    fontFamily: 'Poppins-Medium',
    fontSize: 20,
  },
  titleMedium: {
    fontFamily: 'Poppins-Medium',
    fontSize: 16,
  },
  titleSmall: {
    fontFamily: 'Poppins-Medium',
    fontSize: 14,
  },

  labelLarge: {
    fontFamily: 'Poppins-Medium',
    fontSize: 14,
  },
  labelMedium: {
    fontFamily: 'Poppins-Medium',
    fontSize: 12,
  },
  labelSmall: {
    fontFamily: 'Poppins-Medium',
    fontSize: 11,
  },
  bodyLarge: {
    fontFamily: 'Poppins-Regular',
    fontSize: 16,
  },
  bodyMedium: {
    fontFamily: 'Poppins-Regular',
    fontSize: 14,
  },
  bodySmall: {
    fontFamily: 'Poppins-Regular',
    fontSize: 12,
  },
};

const images = {
  up: Image.resolveAssetSource(require('../assets/up_icon.png')).uri,
  down: Image.resolveAssetSource(require('../assets/down_icon.png')).uri,
};

export const setupTheming = async () => {
  return setTheme({colors, textPresets, images});
};
