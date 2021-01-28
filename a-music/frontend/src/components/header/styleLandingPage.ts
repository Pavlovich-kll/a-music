import styled from 'styled-components';
import LogoImg from './assets/logo-a-m.png';

export default styled.div<{ isScrolled: boolean }>`
  background: ${({ isScrolled }) => (isScrolled ? 'white' : 'rgba(0, 0, 0, 0.15)')};
  transition-duration: 0.7s;
  z-index: 6;
  color: ${({ theme: { secondaryColor } }) => secondaryColor};

  .container-header {
    height: 92px;
    margin-left: calc(50% - 670px);
    max-width: 1340px;
    display: grid;
    grid-template-columns: 2fr 8fr;
    align-items: center;
  }

  .a-music-span-logo {
    font-size: 24px;
    line-height: 160%;
    font-family: Roboto;
    font-style: italic;
    font-weight: bold;
    color: ${({ isScrolled }) => (isScrolled ? '#020303' : 'white')};
  }

  img {
    height: 90%;
    width: auto;
    object-fit: contain;
  }

  .auth-control {
    justify-self: end;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .username {
    text-align: end;
    margin-left: 15px;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .logo-link {
    display: flex;
    align-items: center;
    z-index: 3;
  }

  .logo-img {
    background-image: url(${LogoImg});
    width: 45px;
    height: 43px;
    margin-right: 10px;
  }

  .ant-btn-link {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .link {
    justify-self: end;
    margin: 0 20px;
    background: ${({ theme: { colors } }) => colors.primary.white};
    color: ${({ theme: { colors } }) => colors.primary.baseText};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.brandColor};
    padding: 10px;
    font-weight: 500;
    white-space: nowrap;
  }

  .link:hover {
    background-color: ${({ theme: { colors } }) => colors.primary.bcHover};
    border: 2px solid ${({ theme: { colors } }) => colors.primary.bcHover};
  }

  .logout-link {
    color: ${({ theme: { colors } }) => colors.primary.brandColor};
  }

  .logout-link:hover {
    color: ${({ theme: { colors } }) => colors.primary.bcHover};
  }
`;
