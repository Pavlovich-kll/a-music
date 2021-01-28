import React from 'react';
import { Route, RouteComponentProps, Switch, Redirect } from 'react-router-dom';
import LandingHomePage from '../landing-home-page';
import { connect } from '../../helpers/connect';
import router, { IRouter } from '../../router';

@(connect((state) => ({
  ...state.user,
})) as any)
export default class RouterHandler extends React.Component<any> {
  render() {
    const { current } = this.props;
    const roles = current && current.roles;
    const isManager = roles && roles.find(() => 'manager');

    if (!current) return <LandingHomePage />;
    return (
      <>
        {isManager ? (
          <Switch>
            {router.reduce(
              (allowedRouter: any, { path, component: Component, protectedRoute, isManagerRoute }: IRouter) => {
                if (isManagerRoute && !isManager) return allowedRouter;
                if (!isManagerRoute) return allowedRouter;
                return [
                  ...allowedRouter,
                  <Route
                    key={path}
                    path={path}
                    exact
                    render={(props: RouteComponentProps<any>) => <Component {...props} />}
                  />,
                ];
              },
              []
            )}
            <Redirect push to="/error?error=404" />
          </Switch>
        ) : (
          <Switch>
            {router.reduce(
              (allowedRouter: any, { path, component: Component, protectedRoute, isManagerRoute }: IRouter) => {
                if (!current && protectedRoute) return allowedRouter;
                if (isManagerRoute && isManager) return allowedRouter;
                console.log(allowedRouter, 'allowedRouter');
                console.log(isManagerRoute, 'isManagerRoute');
                return [
                  ...allowedRouter,
                  <Route
                    key={path}
                    path={path}
                    exact
                    render={(props: RouteComponentProps<any>) => <Component {...props} />}
                  />,
                ];
              },
              []
            )}
            <Redirect from="*" to="/auth" />
          </Switch>
        )}
      </>
    );
  }
}
