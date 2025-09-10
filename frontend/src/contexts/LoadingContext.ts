import { createContext } from 'react'
import * as React from "react";

export interface LoadingContextProps {
    isLoading: boolean,
    setIsLoading: React.Dispatch<React.SetStateAction<boolean>>
}

export const LoadingContext = createContext<LoadingContextProps | undefined>(undefined)