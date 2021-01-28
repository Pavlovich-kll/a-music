import styled from 'styled-components';
import { motion } from 'framer-motion';

const DEFAULT_SIDE_LENGTH: string = '250px';

export const ContentCover: any = styled(motion.div).attrs(() => ({
  animate: { opacity: [0, 1] },
  transition: { ease: 'easeIn', duration: 2 },
}))`
  width: ${DEFAULT_SIDE_LENGTH};
  height: ${DEFAULT_SIDE_LENGTH};
  border-radius: 8px;
  position: relative;

  .progressive {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
  }

  .MuiIconButton-root {
    color: #ffffff;
    background: rgba(0, 0, 0, 0.5);

    &:hover {
      background: rgba(0, 0, 0, 0.5);
    }
  }

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    transition: filter 0.3s linear;
  }

  .hoverable {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    background-color: rgba(0, 0, 0, 0);
    border-radius: inherit;
    opacity: 0;
    transition: opacity 0.3s linear;

    &__control-block {
      width: 100%;
      display: flex;
      justify-content: flex-start;
      align-items: flex-end;
    }

    .hoverable__control-wrapper {
      width: 33.33%;
      text-align: center;
    }

    &__description-block,
    &__description-block-title {
      font-weight: bold;

      p {
        margin-bottom: 0;
        text-align: center;
        text-shadow: 2px 2px 15px #000000, -2px -2px 15px #000000, 2px -2px 15px #000000, -2px 2px 15px #000000;
      }

      .author {
        color: #ffffff;
        text-transform: uppercase;
      }

      .album {
        color: cyan;
      }

      .title {
        color: #ffab00;
        text-transform: uppercase;
      }
    }
  }

  &:hover {
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16);

    .hoverable {
      opacity: 1;

      &__description-block,
      &__description-block-title {
        z-index: 9;
      }
    }

    img {
      filter: blur(5px);
    }
  }
`;

export default styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, ${DEFAULT_SIDE_LENGTH});
  width: 100%;
  grid-gap: 8px;
  padding: 8px 0;
  justify-items: center;
  justify-content: center;

  .ant-card-meta-detail {
    .ant-card-meta-title,
    .ant-card-meta-description {
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
    }
  }
`;
