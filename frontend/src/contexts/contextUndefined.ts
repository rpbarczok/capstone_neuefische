import {type Context, useContext} from 'react';
// got this from Nicholas Tower at https://stackoverflow.com/questions/77492225/how-can-i-get-the-setstate-function-in-react-context-and-set-it-as-the-default-v

export const useContextThrowUndefined  = <T>(context: Context<T | undefined>): T => {
    const value = useContext(context)
    if (value === undefined) {
        const name = context.displayName ?? 'Unnamed'
        throw Error(
            `${name} context was used outside of a context provider.`
        )
    }
    return value;
}