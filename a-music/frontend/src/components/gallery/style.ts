import styled from 'styled-components';

export const GalleryStyle: any = styled.div`
  border-bottom: 3px solid black;
  margin-bottom: 20px;
  padding-bottom: 20px;
  color: ${({ theme: { colors } }) => colors.primary.white};

  .title__carousel {
    margin: 30px 0;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
  }

  .image {
    height: 300px;
    width: 300px;
    border-radius: 8px;
  }
  .carousel .horizontalSlider___281Ls {
    height: 380px;
  }

  .slide {
    display: flex;
    align-items: center;
    flex-direction: column;
    height: auto;
    width: 300px;
  }

  .carousel__slider-tray-wrapper .sliderTray___-vHFQ .slide___3-Nqo {
    padding-bottom: 0;
  }

  .carousel__next-button,
  .carousel__back-button {
    background: ${({ theme: { colors } }) => colors.primary.brandColor};
    border: none;
  }

  .carousel__back-button {
    margin-right: 10px;
  }

  .slideInner___2mfX9 {
    display: flex;
    flex-direction: column;
  }

  .title__group {
    margin-top: 10px;
    color: ${({ theme: { colors } }) => colors.primary.baseText};
    text-transform: capitalize;
    white-space: nowrap;
    overflow: hidden;
    max-width: 340px;
    text-overflow: ellipsis;
  }

  .horizontalSlider___281Ls {
    outline: none;
  }

  .carousel__slide-focus-ring {
    outline: none;
  }

  .buttonBack___1mlaL,
  .buttonNext___2mOCa {
    outline: none;
  }
`;
