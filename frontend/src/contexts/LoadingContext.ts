import { createContext } from 'react'

export interface LoadingContextProps {
    isLoading: boolean,
    setIsLoading: (isLoading: boolean) => void
}

export const LoadingContext = createContext<LoadingContextProps | undefined>(undefined)