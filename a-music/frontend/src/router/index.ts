import { lazy } from 'react';
import Collection from '../components/collection/collection';

const LandingHomePage = lazy(() => import('../components/landing-home-page'));
const Home = lazy(() => import('../components/home'));
const SendMusic = lazy(() => import('../components/send-music'));
const SendPlayList = lazy(() => import('../components/create-playlist'));
const PersonalArea = lazy(() => import('../components/personal-area/PersonalArea'));
const Dashboard = lazy(() => import('../components/dashboard'));
const Favorites = lazy(() => import('../components/favorites/favorites'));
const PlaylistPage = lazy(() => import('../components/playlists-page/playlists-page'));
const ErrorPage = lazy(() => import('../components/error-page'));

export interface IRouter {
  path: string;
  component: any;
  protectedRoute: boolean;
  isManagerRoute: boolean;
}

export const AUTH_PATH: string = '/auth';
export const HOME_PATH: string = '/';
export const SENDMUSIC_PATH: string = '/send-music';
export const CREATE_PLAYLIST_PATH: string = '/create-playlist';
export const PERSONALAREA_PATH: string = '/personal-area';
export const COLLECTION_PATH: string = '/collection/:id';
export const DASHBOARD_PATH: string = '/dashboard';
export const FAVORITES_PATH: string = '/favorites';
export const PLAYLIST_PATH: string = '/playlists';
export const ERROR_PATH: string = '/error';

const router: IRouter[] = [
  {
    path: AUTH_PATH,
    component: LandingHomePage,
    protectedRoute: false,
    isManagerRoute: false,
  },
  {
    path: HOME_PATH,
    component: Home,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: SENDMUSIC_PATH,
    component: SendMusic,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: CREATE_PLAYLIST_PATH,
    component: SendPlayList,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: PERSONALAREA_PATH,
    component: PersonalArea,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: COLLECTION_PATH,
    component: Collection,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: DASHBOARD_PATH,
    component: Dashboard,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: FAVORITES_PATH,
    component: Favorites,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: PLAYLIST_PATH,
    component: PlaylistPage,
    protectedRoute: true,
    isManagerRoute: true,
  },
  {
    path: ERROR_PATH,
    component: ErrorPage,
    protectedRoute: true,
    isManagerRoute: true,
  },
];

export default router;
