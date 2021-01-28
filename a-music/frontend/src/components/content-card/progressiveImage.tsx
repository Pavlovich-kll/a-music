import React from 'react';
import ProgressiveImage from 'react-progressive-image';

export const Progressive = ({ cover }: any) => {
  return (
    <ProgressiveImage
      src={`${process.env.API_URL}music-service/content/file/${cover}`}
      placeholder="https://cdn.dribbble.com/users/504740/screenshots/11256241/media/fc00780e26c3eaef22234fc2793cac35.jpg"
    >
      {(src: any) => <img src={src} alt="cover" />}
    </ProgressiveImage>
  );
};
