import { DefaultTheme } from 'styled-components';

const Theme: DefaultTheme = {
  mainColor: '#2c3e50',
  secondaryColor: '#ecf0f1',
  wetAsphaltColor: '#34495e',
  icon: 'light',

  colors: {
    primary: {
      baseText: '#020303',
      brandColor: '#FEDA00',
      white: '#FFFFFF',
      bcHover: '#FFE963',
      bcClicked: '#F2CB00',
      playerColor: '#FEDA00',
    },

    secondary: {
      grayText: '#556170',
      textUnvisible: '#828282',
      cream: '#FAFAFA',
      creamDark: '#EBEBEB',
      lines: '#E0E0E0',
      inputsStroke: '#ADADAD',
      inputsStrokeDark: '8C8C8C',
    },
  },

  buttons: {
    small: {
      width: '128px',
      height: '48px',
    },
    medium: {
      width: '190px',
      height: '48px',
    },
    large: {
      width: '290px',
      height: '48px',
    },
  },

  spaces: {
    betweenBlocks: '100px',
    contentLarge: '60px',
    contentMedium: '40px',
    contentSmall: '20px',
    contentSmallest: '10px',
  },
};

export { Theme };
