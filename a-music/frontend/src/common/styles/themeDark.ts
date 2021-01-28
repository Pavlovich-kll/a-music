import { DefaultTheme } from 'styled-components';

const ThemeDark: DefaultTheme = {
  mainColor: '#2c3e50',
  secondaryColor: '#ecf0f1',
  wetAsphaltColor: '#34495e',
  icon: 'dark',

  colors: {
    primary: {
      baseText: '#fff',
      brandColor: '#FEDA00',
      white: '#000',
      bcHover: '#FFE963',
      bcClicked: '#F2CB00',
      playerColor: '#F2CB00',
    },

    secondary: {
      grayText: '#CCD5D5',
      whiteText: '#fff',
      textUnvisible: '#828282',
      cream: '#2C2C4D',
      creamDark: '#222042',
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

export { ThemeDark };
