import React from 'react';
import { Button } from 'antd';
import AsideView from './styles';
import { ExploreSVG, FavoritesSVG, PlaylistsSVG, DashboardSVG } from './svg/aside-nav-svg';
import { NavLink } from 'react-router-dom';

interface IAsideProps {
  title?: string;
}

const data = [
  { link: '/', name: 'Explore', IconSvg: ExploreSVG },
  { link: '/favorites', name: 'Favorites', IconSvg: FavoritesSVG },
  { link: '/playlists', name: 'Playlists', IconSvg: PlaylistsSVG },
  { link: '/dashboard', name: 'Dashboard', IconSvg: DashboardSVG },
];

const Aside = ({ title }: IAsideProps) => {
  return (
    <AsideView>
      {!!title && (
        <header>
          <h5>{title}</h5>
          <Button htmlType="button" type="link">
            &times;
          </Button>
        </header>
      )}
      {data.map((data: any) => {
        return (
          <NavLink key={data.name} className="nav-item" exact activeClassName={'active-item'} to={data.link}>
            <div className="nav-border">
              <data.IconSvg />
              <span className="nav-name">{data.name}</span>
              <div className="active-block" />
            </div>
          </NavLink>
        );
      })}
    </AsideView>
  );
};

export default Aside;
